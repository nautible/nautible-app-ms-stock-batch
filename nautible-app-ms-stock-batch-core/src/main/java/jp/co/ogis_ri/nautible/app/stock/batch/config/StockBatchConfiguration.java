package jp.co.ogis_ri.nautible.app.stock.batch.config;

import io.quarkus.runtime.annotations.StaticInitSafe;
import io.smallrye.config.ConfigMapping;

@StaticInitSafe
@ConfigMapping(prefix = "stock")
public interface StockBatchConfiguration {

    Service service();

    QuantityThreshold quantityThreshold();

    interface Service {
        String name();
        String path();
    }

    interface QuantityThreshold {
        int warning();
        int severe();
    }
}
