CREATE TABLE `productos` (
  `id` varchar(255) PRIMARY KEY,
  `name` varchar(255) NOT NULL,
  `weighted_price` decimal DEFAULT 0
);
