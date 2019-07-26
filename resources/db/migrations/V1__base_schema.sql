-- Trigger for updated_at
--CREATE OR REPLACE FUNCTION trigger_updated_at()
--RETURNS TRIGGER AS $$
--BEGIN
--  NEW.updated_at = NOW() AT TIME ZONE 'UTC';
--  RETURN NEW;
--END;
--$$ LANGUAGE plpgsql;

-- Channel table
create table channel
(
	id INT GENERATED ALWAYS AS IDENTITY,
	title VARCHAR(50) NOT NULL,
	logo TEXT,
	created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT (NOW() AT TIME ZONE 'UTC'),
	updated_at TIMESTAMP,
	archived BOOLEAN DEFAULT TRUE,
	rank INT DEFAULT 0
);

create unique index channel_title_uindex on channel (title);

-- Add updated_at trigger for channel table
--CREATE TRIGGER trigger_channel_updated_at
--BEFORE UPDATE ON channel
--FOR EACH ROW
--EXECUTE PROCEDURE trigger_updated_at();
