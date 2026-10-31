-- Fix admin user password hash (was incorrect BCrypt hash)
UPDATE app_user SET password_hash = '$2a$10$qegul0Qf7pkNeT8nS0ra0.B6R8uFGKvNUsm2.MmyZDvKFtbPXNqvO' WHERE email = 'admin@turbometa.local';
