//package fr.dauphine.miageIf.Calendrier;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class CalendrierService {
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    private final String siteServiceUrl = "http://site-service-url/api/sites/";
//
//    public Site getSiteById(Long siteId) {
//        String url = siteServiceUrl + siteId;
//        return restTemplate.getForObject(url, Site.class);
//    }
//}
