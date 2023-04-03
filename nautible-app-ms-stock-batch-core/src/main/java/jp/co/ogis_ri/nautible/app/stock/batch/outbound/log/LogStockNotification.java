package jp.co.ogis_ri.nautible.app.stock.batch.outbound.log;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import jp.co.ogis_ri.nautible.app.stock.batch.client.rest.RestStock;
import jp.co.ogis_ri.nautible.app.stock.batch.config.StockBatchConfiguration.QuantityThreshold;
import jp.co.ogis_ri.nautible.app.stock.batch.domain.StockNotification;

@ApplicationScoped
public class LogStockNotification implements StockNotification {

    private static final Logger LOG = Logger.getLogger(LogStockNotification.class.getName());
    private static final String SEVERE_LOG_MESSAGE = "No stock. QuantityThreshold={0}, Stock={1}";
    private static final String WARNING_LOG_MESSAGE = "Low stock. QuantityThreshold={0}, Stock={1}";

    @Override
    public void notify(RestStock stock, QuantityThreshold quantityThreshold) {
        if (stock.getQuantity() <= quantityThreshold.severe()) {
            LOG.log(Level.SEVERE, SEVERE_LOG_MESSAGE,
                new String[] { String.valueOf(quantityThreshold.severe()), stock.toString() });
        } else {
            LOG.log(Level.WARNING, WARNING_LOG_MESSAGE,
                new String[] { String.valueOf(quantityThreshold.warning()), stock.toString() });
        }
    }
    
}
