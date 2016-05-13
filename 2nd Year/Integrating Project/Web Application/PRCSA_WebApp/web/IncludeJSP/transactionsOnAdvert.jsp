<!--Generating a table of transactions on an advert-->
<%@ page import="pagefillers.MemberTransactions" %>
<%@ page import="entities.Member" %>

<div class="table-responsive">
    <table class="table table-striped">
        <thead>
            <tr>
                <th>Advert Title</th>
                <th>Member</th>
                <th>Credits Received</th>
                <th>Date Completed</th>
                <th>Review Received</th>
            </tr>
        </thead>
        <tbody>
            <%
                MemberTransactions transactions = new MemberTransactions();
                String advertID = request.getParameter("addID").toString();
                Member member = (Member)session.getAttribute("member");
                out.println(transactions.getTransactionsByAdvertID(advertID, member));
            %>
        </tbody>
    </table>
</div>
