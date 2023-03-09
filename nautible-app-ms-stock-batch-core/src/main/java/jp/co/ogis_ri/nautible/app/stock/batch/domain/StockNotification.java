package jp.co.ogis_ri.nautible.app.stock.batch.domain;

import jp.co.ogis_ri.nautible.app.stock.batch.config.StockBatchConfiguration.QuantityThreshold;
import jp.co.ogis_ri.nautible.app.stock.batch.client.rest.RestStock;

public interface StockNotification {
    default boolean shouldNofity(RestStock stock, QuantityThreshold quantityThreshold) {
        return stock.getQuantity() <= quantityThreshold.warning();
    }

    void notify(RestStock stock, QuantityThreshold quantityThreshold);
}
