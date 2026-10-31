-- Create app_user table
CREATE TABLE IF NOT EXISTS app_user (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username VARCHAR(30) NOT NULL,
    email TEXT UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    active BOOLEAN NOT NULL DEFAULT 1,
    is_admin BOOLEAN NOT NULL DEFAULT 0
);

-- Create turbo table
CREATE TABLE IF NOT EXISTS turbo (
    ref TEXT PRIMARY KEY,
    brand TEXT NOT NULL,
    model TEXT NOT NULL,
    geo TEXT
);

-- Insert default admin user (password: admin)
INSERT INTO app_user (username, email, password_hash, active, is_admin)
VALUES ('Admin', 'admin@turbometa.local', '$2a$10$qegul0Qf7pkNeT8nS0ra0.B6R8uFGKvNUsm2.MmyZDvKFtbPXNqvO', 1, 1);
