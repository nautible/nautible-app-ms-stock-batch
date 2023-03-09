package jp.co.ogis_ri.nautible.app.stock.batch.outbound.rest;

import jp.co.ogis_ri.nautible.app.stock.batch.config.StockBatchConfiguration;
import jp.co.ogis_ri.nautible.app.stock.batch.domain.StockServiceClient;
import jp.co.ogis_ri.nautible.app.stock.batch.client.rest.RestFindAllResponse;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.dapr.client.DaprClient;
import io.dapr.client.domain.HttpExtension;

@ApplicationScoped
public class DaprStockServiceClient implements StockServiceClient {

    private static final Logger LOG = Logger.getLogger(DaprStockServiceClient.class.getName());
    private static final String INFO_LOG_MESSAGE = "[{0}] invoke service method with dapr. serviceName={1}";

    @Inject
    StockBatchConfiguration config;

    @Inject
    DaprClient daprClient;

    public Optional<RestFindAllResponse> findAllStock() {
        String stockServiceName = config.service().name();
        String stockServicePath = config.service().path();

        LOG.log(Level.INFO, INFO_LOG_MESSAGE, new String[] { "START", stockServiceName });
        return Optional.ofNullable(
                daprClient.invokeMethod(stockServiceName,
                                        stockServicePath,
                                        null,
                                        HttpExtension.GET,
                                        RestFindAllResponse.class)
                          .doFinally(onFinaly -> LOG.log(Level.INFO, INFO_LOG_MESSAGE, new String[] { "END", stockServiceName }))
                          .block());
    }
}
