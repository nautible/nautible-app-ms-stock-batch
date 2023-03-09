package jp.co.ogis_ri.nautible.app.stock.batch.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;

@ApplicationScoped
public class StockBatchBeanConfiguration {
    
    @Produces
    public DaprClient daprClient() {
        return new DaprClientBuilder().build();
    }
}
