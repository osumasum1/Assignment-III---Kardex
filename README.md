# Kardex

## Diagram

![Diagram](https://github.com/osumasum1/KudosProject/blob/master/DiagramKudos.png)

## Kardex Service

Kardex service was developed with DropWizard and Java. It uses MongoDB.
The following end-points were developed fot this service.
* POST /kardex (kardex.resources.KardexResource)
* PUT /kardex/updatePrice/{idProducto}/{date}

### RabbitMQ
* Point-to-point pattern used to create a kardex. Receiver is located in storeKardex.
* Point-to-point pattern used to update a kardex. Receiver is located in storeKardex.


## Products Service

Users service was developed with DropWizard and Java. It uses MYSQL DB.
The following end-points were developed fot this service.
* POST    /products
* GET     /products/{id}

## StoreKardex Service

StoreKardex service was developed with DropWizard and Java. It uses MongoDB.
The following end-points were developed fot this service.
* POST    /storeKardex
* PUT     /storeKardex/updatePrice/{idProducto}/{date}

### RabbitMQ
* Point-to-point pattern used to notify that the kardex was created. Receiver is located in stats.
* Point-to-point pattern used to notify that the kardex was updated. Receiver is located in stats.

## Stats Service

Stats service was developed with DropWizard and Java. It uses MYSQL DB and Mongo.
The following end-points were developed fot this service.
* PUT     /stats
* GET     /stats/weightedPrice/{id}
 
### RabbitMQ

* Point-to-point pattern used to update weighted price when a kardex is added or updated.

