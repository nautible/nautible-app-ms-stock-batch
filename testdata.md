# 動作確認用テストデータ

## 在庫アプリケーション側データ作成
```
# 正常データ
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{ \"productId\": \"1\",\"quantity\": \"1000\",\"reservedQuantity\": \"0\"}" http://localhost:8080/stock

# severeデータ(在庫数量0)
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{ \"productId\": \"2\",\"quantity\": \"0\",\"reservedQuantity\": \"0\"}" http://localhost:8080/stock

# warningデータ(在庫数量5以下)
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{ \"productId\": \"3\",\"quantity\": \"1\",\"reservedQuantity\": \"0\"}" http://localhost:8080/stock
```

## 在庫(バッチ)アプリケーション実行結果

```
xxxx-xx-xx xx:xx:xx,xxx SEVERE [jp.co.ogi.nau.app.sto.bat.out.log.LogStockNotification] (main) No stock. QuantityThreshold=0, Stock=class RestStock {
    id: 2
    productId: 2
    quantity: 0
    reservedQuantity: 0
}
xxxx-xx-xx xx:xx:xx,xxx WARNING [jp.co.ogi.nau.app.sto.bat.out.log.LogStockNotification] (main) Low stock. QuantityThreshold=5, Stock=class RestStock {
    id: 3
    productId: 3
    quantity: 1
    reservedQuantity: 0
}
```
