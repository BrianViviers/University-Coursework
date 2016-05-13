/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author BrianV
 */
@javax.ws.rs.ApplicationPath("resources")
public class ApplicationConfig extends Application {

    /**
     *
     * @return
     */
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(webservices.BidConfirmREST.class);
        resources.add(webservices.CountBidsREST.class);
        resources.add(webservices.CountTransactionsREST.class);
        resources.add(webservices.CreateAdvertREST.class);
        resources.add(webservices.CreateBidREST.class);
        resources.add(webservices.FinishTransactionREST.class);
        resources.add(webservices.GetAdvertByIdREST.class);
        resources.add(webservices.GetAdvertImageByIdREST.class);
        resources.add(webservices.GetAllRulesREST.class);
        resources.add(webservices.GetBidsREST.class);
        resources.add(webservices.GetCurrentAdvertImagesREST.class);
        resources.add(webservices.GetCurrentAdvertsREST.class);
        resources.add(webservices.GetMemberDetailsREST.class);
        resources.add(webservices.GetMemberPasswordREST.class);
        resources.add(webservices.GetMemberReviewsREST.class);
        resources.add(webservices.GetMembersREST.class);
        resources.add(webservices.GetPastAdvertsImagesREST.class);
        resources.add(webservices.GetPastAdvertsREST.class);
        resources.add(webservices.GetSearchAdvertsREST.class);
        resources.add(webservices.GetSearchedAdvertsImagesREST.class);
        resources.add(webservices.GetTransactionsREST.class);
        resources.add(webservices.NewCrossOriginResourceSharingFilter.class);
        resources.add(webservices.RecordUserSessionsREST.class);
        resources.add(webservices.RegisterMemberREST.class);
        resources.add(webservices.RemoveAdvertREST.class);
        resources.add(webservices.UpdateMemberDetailsREST.class);
        resources.add(webservices.UploadAdvertImageREST.class);
    }
    
}
