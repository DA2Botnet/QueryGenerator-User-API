public class QueryGenerator {

    private String name;

    private String ip;

    private Ports request;

    private Ports receive;

    private BWMessages request_message;

    public QueryGenerator(String ip) {
        this.name = ip;
        this.ip = ip;

        request = BWPorts.QUERY_REQUEST;
        receive = BWPorts.QUERY_RECEIVE;

        request_message = BWMessages.QUERY_REQUEST_MESSAGE;

    }

    public QueryGenerator(String ip, Ports request, Ports receive) {
        this.name = ip;
        this.ip = ip;
        this.request = request;
        this.receive = receive;

        request_message = BWMessages.QUERY_REQUEST_MESSAGE;
        
    }


    /**
     * Sends a request for a query
     * 
     * @return The search query
     * 
     * @param request Request port
     * @param receive Receive port
     */

    public Query requestQuery() {
        // Local var setup
        String response = "";

        // Setup request client
        ClientUDP send = new ClientUDP(ip, receive);
        send.startClient();

        // Send & close
        send.sendMessage(request_message);
        send.closeClient();

        // Start server
        ServerUDP get = new ServerUDP(request);
        get.startServer();
        
        // Wait for responses
        response = get.getMessage();

        // Close the server
        get.closeServer();

        // Return the query
        return new Query(response, false);

    }


}