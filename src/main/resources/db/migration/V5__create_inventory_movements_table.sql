CREATE TABLE inventory_movements (
    id UUID PRIMARY KEY,
    product_id UUID NOT NULL,
    user_id UUID NOT NULL,
    type VARCHAR(20) NOT NULL,
    movement_price NUMERIC(10,2),
    observation VARCHAR(500),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_inventory_movements_product
            FOREIGN KEY (product_id)
            REFERENCES products(id),

    CONSTRAINT fk_inventory_movements_user
            FOREIGN KEY (user_id)
            REFERENCES users(id)
);