<%@ page import="pagefillers.MemberBids" %>
<%@ page import="entities.Member" %>
<!--Constructing a table of bids on an advert-->
<div class="table-responsive">
    <table class="table table-striped">
        <thead>
            <tr>
                <th>Advert Title</th>
                <th>Message</th>
                <th>Bid Date</th>
                <th>Member Name</th>
                <th>Accept/Reject</th>
            </tr>
        </thead>
        <tbody>
            <%
                Member member = (Member)session.getAttribute("member");
                MemberBids bids = new MemberBids();
                String advertID = request.getParameter("addID").toString();
                out.println(bids.getBidsByAdvertID(advertID, member));
            %>
        </tbody>
    </table>
</div>