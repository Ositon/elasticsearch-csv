package org.xbib.elasticsearch.rest.csv;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.rest.RestChannel;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.action.search.RestSearchAction;

import static org.elasticsearch.rest.RestRequest.Method.GET;
import static org.elasticsearch.rest.RestRequest.Method.POST;

public class CSVRestSearchAction extends BaseRestHandler {

    @Inject
    public CSVRestSearchAction(Settings settings, Client client, RestController controller) {
        super(settings, controller, client);
        controller.registerHandler(GET, "/_search_csv", this);
        controller.registerHandler(POST, "/_search_csv", this);
        controller.registerHandler(GET, "/{index}/_search_csv", this);
        controller.registerHandler(POST, "/{index}/_search_csv", this);
        controller.registerHandler(GET, "/{index}/{type}/_search_csv", this);
        controller.registerHandler(POST, "/{index}/{type}/_search_csv", this);
        controller.registerHandler(GET, "/_search_csv/template", this);
        controller.registerHandler(POST, "/_search_csv/template", this);
        controller.registerHandler(GET, "/{index}/_search_csv/template", this);
        controller.registerHandler(POST, "/{index}/_search_csv/template", this);
        controller.registerHandler(GET, "/{index}/{type}/_search_csv/template", this);
        controller.registerHandler(POST, "/{index}/{type}/_search_csv/template", this);
    }

    @Override
    public void handleRequest(final RestRequest request, final RestChannel channel, final Client client) {
        SearchRequest searchRequest = RestSearchAction.parseSearchRequest(request);
        searchRequest.listenerThreaded(false);
        client.search(searchRequest, new CSVToXContentListener(channel,
                request.paramAsStringArray("keys", null),
                request.paramAsBoolean("with_index", false),
                        request.paramAsBoolean("with_type", false),
                        request.paramAsBoolean("with_id", false))
        );
    }

}
