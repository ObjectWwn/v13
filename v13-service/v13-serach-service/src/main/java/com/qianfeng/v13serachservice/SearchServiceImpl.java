package com.qianfeng.v13serachservice;

import com.alibaba.dubbo.config.annotation.Service;
import com.pojo.PageResultBean;
import com.pojo.ResultBean;
import com.qianfeng.serarch.ISerarchService;
import com.qianfeng.v13.entity.TProduct;
import com.qianfeng.v13.mapper.TProductMapper;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author wwn
 * @Date 2019/6/17
 */
@Service
public class SearchServiceImpl implements ISerarchService, Serializable {

    @Autowired
    private TProductMapper tProductMapper;

    @Autowired
    private SolrClient solrClient;

    @Override
    public ResultBean synAllData() {
        //1,把数据从数据库拿出来
        List<TProduct> list = tProductMapper.list();
        //2，将数据拼接好product->document
        for (TProduct tProduct : list) {
            SolrInputDocument document = new SolrInputDocument();
            document.setField("id", tProduct.getId());
            document.setField("product_name", tProduct.getName());
            document.setField("product_images", tProduct.getImages());
            document.setField("product_price", tProduct.getPrice());
            //保存到solr库中'
            try {
                solrClient.add(document);
            } catch (SolrServerException | IOException e) {
                e.printStackTrace();
                return new ResultBean("404", "数据提交到solr失败");
            }
        }
        try {
            solrClient.commit();
            //3，将数据同步到solr的data中
            return new ResultBean("200", "数据同步成功");
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return new ResultBean("404", "数据提交到solr失败");
        }

    }

    @Override
    public ResultBean synById(Long id) {
        TProduct tProduct = tProductMapper.selectByPrimaryKey(id);
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id", tProduct.getId());
        document.setField("product_name", tProduct.getName());
        document.setField("product_images", tProduct.getImages());
        document.setField("product_price", tProduct.getPrice());
        //保存到solr库中'
        try {
            //删除
            if (false==tProduct.getFlag()){
                String ID = id+"";
                solrClient.deleteById(ID);
            }else{
                solrClient.add(document);
            }
            solrClient.commit();
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return new ResultBean("404", "数据提交到solr失败");
        }
        return new ResultBean("200", "数据提交成功");
    }

  /*  @Override
    public ResultBean queryByKeyWords(String keyWords) {
        return null;
    }*/

    @Override
    public ResultBean queryByKeyWords(String keyWords, int pageNum) {
        PageResultBean page = new PageResultBean();
        List<TProduct> list1 = tProductMapper.list();
        int total = list1.size();
        //总条数
        page.setTotal(total);
        //每页大小
        page.setPageSize(3);
        //导航页码数
        page.setNavigatePages(1);
        //总页数
        page.setPages((total % page.getPageSize()) == 0 ? total / page.getPageSize() : (total / page.getPageSize()) + 1);
        //所有导航页号
        int[] pageNums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        page.setNavigatepageNums(pageNums);
        //当前页
        if (pageNum == 0) {
            page.setPageNum(1);
        } else {
            page.setPageNum(pageNum);
        }
        //1,拼接查询
        SolrQuery solrQuery = new SolrQuery();
        if (StringUtils.isEmpty(keyWords)) {
            solrQuery.setQuery("*:*");
        } else {
            solrQuery.setQuery("product_name:" + keyWords);
            solrQuery.setStart((pageNum - 1) * page.getPageSize() + 1);
            solrQuery.setRows(page.getPageSize());
        }
        //设置高亮
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("product_name");
        solrQuery.setHighlightSimplePre("<font color='red'>");
        solrQuery.setHighlightSimplePost("</font>");

        try {
            QueryResponse response = solrClient.query(solrQuery);
            SolrDocumentList results = response.getResults();
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            //2,document->TProduct
            List<TProduct> list = new ArrayList<>(results.size());
            for (SolrDocument document: results){
                    TProduct tProduct = new TProduct();
                    tProduct.setId(Long.parseLong(document.getFieldValue("id").toString()));
                    //tProduct.setName(document.getFieldValue("product_name").toString());
                    tProduct.setPrice(Long.parseLong(document.getFieldValue("product_price").toString()));
                    tProduct.setImages(document.getFieldValue("product_images").toString());

                    Map<String, List<String>> idHigh = highlighting.get(document.getFieldValue("id"));
                    List<String> productNameHigh = idHigh.get("product_name");
                    if (productNameHigh == null || productNameHigh.isEmpty()) {
                        tProduct.setName(document.getFieldValue("product_name").toString());
                    } else {
                        tProduct.setName(productNameHigh.get(0));
                    }
                    list.add(tProduct);
                }
                //3,返回
                page.setList(list);
                return new ResultBean("200", page);
            } catch(SolrServerException | IOException e){
                e.printStackTrace();
                return new ResultBean("404", "查询失败");
            }


    }

    @Override
    public ResultBean queryByKeyWords(String keyWords) {
        //1,查询
        SolrQuery solrQuery = new SolrQuery();
        if(StringUtils.isEmpty(keyWords)){
            solrQuery.setQuery("*:*");
        }else{
            solrQuery.setQuery("product_name:"+keyWords);
        }

        try {
            QueryResponse response = solrClient.query(solrQuery);
            SolrDocumentList results = response.getResults();
            //2,document->TProduct
            List<TProduct> list = new ArrayList<>(results.size());
            for (SolrDocument document: results){
                TProduct tProduct = new TProduct();
                tProduct.setId(Long.parseLong(document.getFieldValue("id").toString()));
                tProduct.setName(document.getFieldValue("product_name").toString());
                tProduct.setPrice(Long.parseLong(document.getFieldValue("product_price").toString()));
                tProduct.setImages(document.getFieldValue("product_images").toString());

                list.add(tProduct);
            }
            //3,返回
            return new ResultBean("200",list);
        } catch (SolrServerException  |IOException e) {
            e.printStackTrace();
            return new ResultBean("404","查询失败");
        }

    }
}
