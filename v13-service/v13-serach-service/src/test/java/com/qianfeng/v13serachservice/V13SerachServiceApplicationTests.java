package com.qianfeng.v13serachservice;

import com.pojo.ResultBean;
import com.qianfeng.serarch.ISerarchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V13SerachServiceApplicationTests {

    @Autowired
    private SolrClient solrClient;

    @Autowired
    private ISerarchService serarchService;

    @Test
    public void  synAllData(){
        //ResultBean resultBean = serarchService.synAllData();
        ResultBean resultBean = serarchService.synById(2L);
        System.out.println(resultBean.getStatusCode());
    }

    @Test
    public void queryByKeyWords(){
        ResultBean resultBean = serarchService.queryByKeyWords("华为",1);
        System.out.println(resultBean.getData().equals(null));
    }

    @Test
    public  void  addOrUpdateTest() throws IOException, SolrServerException {
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id",2);
        document.setField("product_name","华为手机");
        document.setField("product_images",null);
        document.setField("product_price",3000);
        //保存到solr库中'
        solrClient.add(document);
        //提交
        solrClient.commit();
    }

    @Test
    public  void  queryTest() throws IOException, SolrServerException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("product_name:华为手机");

        QueryResponse response = solrClient.query(solrQuery);
        SolrDocumentList results = response.getResults();

        for (SolrDocument document : results){
            System.out.println("product_name"+document.get("product_name"));
        }
    }

    @Test
    public  void  deleteTest() throws IOException, SolrServerException {
       /* solrClient.deleteById("1");
        solrClient.commit();*/
       //关键词有华为的全删掉
       solrClient.deleteByQuery("product_name:华为");
       solrClient.commit();
    }

    @Test
    public void contextLoads() {

    }

}
