package jp.co.ogis_ri.nautible.app.stock.batch.domain;

import java.util.Optional;

import jp.co.ogis_ri.nautible.app.stock.batch.client.rest.RestFindAllResponse;

public interface StockServiceClient {
    Optional<RestFindAllResponse> findAllStock();
}
