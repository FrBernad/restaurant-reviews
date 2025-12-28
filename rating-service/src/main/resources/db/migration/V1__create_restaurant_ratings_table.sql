CREATE TABLE restaurant_ratings
(
    restaurant_id UUID PRIMARY KEY,
    rating_sum    BIGINT      NOT NULL,
    rating_count  BIGINT      NOT NULL,
    updated_at    TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_restaurant_ratings_updated_at ON restaurant_ratings (updated_at DESC);