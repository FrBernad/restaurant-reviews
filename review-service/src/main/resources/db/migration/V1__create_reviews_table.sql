CREATE TABLE reviews
(
    id            UUID PRIMARY KEY,
    restaurant_id UUID        NOT NULL,
    user_id       UUID        NOT NULL,
    rating        INTEGER     NOT NULL,
    comment       VARCHAR(2000),
    created_at    TIMESTAMPTZ NOT NULL
);

ALTER TABLE reviews
    ADD CONSTRAINT uk_reviews_restaurant_user UNIQUE (restaurant_id, user_id);

-- Indexes
CREATE INDEX idx_reviews_restaurant_created_at
    ON reviews (restaurant_id, created_at DESC);

CREATE INDEX idx_reviews_user_id
    ON reviews (user_id);
