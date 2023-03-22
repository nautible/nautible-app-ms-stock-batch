package jp.co.ogis_ri.nautible.app.stock.batch.domain;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import jp.co.ogis_ri.nautible.app.stock.batch.config.StockBatchConfiguration;
import jp.co.ogis_ri.nautible.app.stock.batch.config.StockBatchConfiguration.QuantityThreshold;

@ApplicationScoped
public class StockBatchService {

    private static final Logger LOG = Logger.getLogger(StockBatchService.class.getName());
    private static final String INFO_LOG_MESSAGE = "[{0}] subscribe service method response.";

    @Inject
    StockServiceClient stockServiceClient;

    @Inject
    StockBatchConfiguration config;

    @Inject
    StockNotification stockNotification;

    public void checkStockQuantity() {
        // データ量が少ないためDaprのServiceInvocationを用いて、
        // Stockサービスから全Stockを取得する同期連携で処理しているが、
        // データ量によってはDaprのPublish/Subscribeを用いるなど非同期連携も検討した方が良い
        stockServiceClient.findAllStock().ifPresent(response -> {
            LOG.log(Level.INFO, INFO_LOG_MESSAGE, "START");

            QuantityThreshold quantityThreshold = config.quantityThreshold();

            response.getStocks().stream()
                .filter(stock -> stockNotification.shouldNofity(stock, quantityThreshold))
                .forEachOrdered(stock -> stockNotification.notify(stock, quantityThreshold));

            LOG.log(Level.INFO, INFO_LOG_MESSAGE, "END");
        });
    }
}
