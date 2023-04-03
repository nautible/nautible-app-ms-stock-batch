package jp.co.ogis_ri.nautible.app.stock.batch.inbound.cmd;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Named;

import io.dapr.client.DaprClient;
import io.quarkus.picocli.runtime.annotations.TopCommand;
import jp.co.ogis_ri.nautible.app.stock.batch.domain.StockBatchService;
import picocli.CommandLine.Command;

@TopCommand
@Named("quantity-check")
@Command(name = "quantity-check", mixinStandardHelpOptions = true)
public class CmdStockQuantityCheckService implements Runnable {

    private static final Logger LOG = Logger.getLogger(CmdStockQuantityCheckService.class.getName());
    private static final String INFO_LOG_MESSAGE = "[{0}] run StockBatchService#checkStockQuantity.";
    private static final String SEVERE_LOG_MESSAGE = "cause Exception.";
    private static final String INFO_DAPR_LOG_MESSAGE = "[{0}] close&shutdown DaprClient.";

    @Inject
    StockBatchService stockBatchService;

    @Inject
    DaprClient daprClient;

    @Override
    public void run() {
        LOG.log(Level.INFO, INFO_LOG_MESSAGE, "START");

        try {
            stockBatchService.checkStockQuantity();
            LOG.log(Level.INFO, INFO_LOG_MESSAGE, "END");
        } catch (Exception e) {
            LOG.log(Level.INFO, INFO_LOG_MESSAGE, "ERROR_END");
            LOG.log(Level.SEVERE, SEVERE_LOG_MESSAGE, e);
            throw new RuntimeException(e);
        } finally {
            // https://docs.dapr.io/operations/hosting/kubernetes/kubernetes-job/
            if (Objects.nonNull(daprClient)) {
                try {
                    LOG.log(Level.INFO, INFO_DAPR_LOG_MESSAGE, "START");
                    daprClient.shutdown().block();
                    daprClient.close();
                } catch (Exception e) {
                    LOG.log(Level.INFO, INFO_DAPR_LOG_MESSAGE, "ERROR_END");
                    LOG.log(Level.SEVERE, SEVERE_LOG_MESSAGE, e);
                    throw new RuntimeException(e);
                } finally {
                    LOG.log(Level.INFO, INFO_DAPR_LOG_MESSAGE, "END");
                }
            }
        }
    }
}
