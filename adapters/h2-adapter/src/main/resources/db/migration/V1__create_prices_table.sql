-- db/migration/V1__create_prices_table.sql

-- Create prices table
CREATE TABLE IF NOT EXISTS prices (
    id INT AUTO_INCREMENT PRIMARY KEY,
    brand_id INT,
    start_date DATETIME,
    end_date DATETIME,
    price_list INT,
    product_id INT,
    priority INT,
    price DECIMAL(10, 2),
    curr VARCHAR(3)
);

-- Create an index on start_date, end_date, and priority for efficient querying
CREATE INDEX idx_prices_dates_priority ON prices (start_date, end_date, priority);
