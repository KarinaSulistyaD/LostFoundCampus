-- LostFoundKampus database bootstrap (MySQL 8+)
-- Run as a privileged account (example: root)

CREATE DATABASE IF NOT EXISTS lostfoundkampus
	CHARACTER SET utf8mb4
	COLLATE utf8mb4_unicode_ci;

-- Application DB user (update password as needed)
CREATE USER IF NOT EXISTS 'lfk_app'@'localhost' IDENTIFIED BY 'lfk_app_123';
GRANT ALL PRIVILEGES ON lostfoundkampus.* TO 'lfk_app'@'localhost';
FLUSH PRIVILEGES;

USE lostfoundkampus;

CREATE TABLE IF NOT EXISTS users (
	id INT AUTO_INCREMENT PRIMARY KEY,
	username VARCHAR(50) NOT NULL,
	password VARCHAR(255) NOT NULL,
	nama VARCHAR(100) NOT NULL,
	role ENUM('admin', 'user') NOT NULL DEFAULT 'user',
	created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	CONSTRAINT uq_users_username UNIQUE (username)
);

CREATE TABLE IF NOT EXISTS barang (
	id INT AUTO_INCREMENT PRIMARY KEY,
	nama_barang VARCHAR(100) NOT NULL,
	kategori VARCHAR(50) NOT NULL,
	deskripsi TEXT,
	lokasi VARCHAR(100) NOT NULL,
	status ENUM('Hilang', 'Ditemukan') NOT NULL,
	status_claim ENUM('Belum Diklaim', 'Sudah Diklaim', 'Sudah Ditemukan')
		NOT NULL DEFAULT 'Belum Diklaim',
	user_id INT NOT NULL,
	claimed_by_user_id INT NULL,
	created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	CONSTRAINT fk_barang_user
		FOREIGN KEY (user_id) REFERENCES users(id)
		ON UPDATE CASCADE
		ON DELETE RESTRICT
	,
	CONSTRAINT fk_barang_claimed_by_user
		FOREIGN KEY (claimed_by_user_id) REFERENCES users(id)
		ON UPDATE CASCADE
		ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS claim_requests (
	id INT AUTO_INCREMENT PRIMARY KEY,
	barang_id INT NOT NULL,
	requester_user_id INT NOT NULL,
	status ENUM('Pending', 'Approved', 'Rejected') NOT NULL DEFAULT 'Pending',
	reviewed_by_user_id INT NULL,
	requested_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	reviewed_at TIMESTAMP NULL DEFAULT NULL,
	CONSTRAINT fk_claim_requests_barang
		FOREIGN KEY (barang_id) REFERENCES barang(id)
		ON UPDATE CASCADE
		ON DELETE CASCADE,
	CONSTRAINT fk_claim_requests_requester
		FOREIGN KEY (requester_user_id) REFERENCES users(id)
		ON UPDATE CASCADE
		ON DELETE CASCADE,
	CONSTRAINT fk_claim_requests_reviewer
		FOREIGN KEY (reviewed_by_user_id) REFERENCES users(id)
		ON UPDATE CASCADE
		ON DELETE SET NULL
);

CREATE INDEX idx_barang_user_id ON barang(user_id);
CREATE INDEX idx_barang_claimed_by_user_id ON barang(claimed_by_user_id);
CREATE INDEX idx_barang_status ON barang(status);
CREATE INDEX idx_barang_kategori ON barang(kategori);
CREATE INDEX idx_claim_requests_barang_id ON claim_requests(barang_id);
CREATE INDEX idx_claim_requests_requester_user_id ON claim_requests(requester_user_id);
CREATE INDEX idx_claim_requests_status ON claim_requests(status);

-- Seed users
INSERT INTO users (id, username, password, nama, role)
SELECT 1, 'admin', 'admin123', 'Administrator', 'admin'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE id = 1);

INSERT INTO users (id, username, password, nama, role)
SELECT 2, 'user1', 'user123', 'Karina Sulistya', 'user'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE id = 2);

INSERT INTO users (id, username, password, nama, role)
SELECT 3, 'user2', 'user123', 'Lathiva Almasea', 'user'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE id = 3);

-- Seed barang
INSERT INTO barang (nama_barang, kategori, deskripsi, lokasi, status, status_claim, user_id)
SELECT 'Laptop ASUS VivoBook', 'Elektronik', 'Warna silver, ada stiker kampus', 'Perpustakaan Lt. 2', 'Hilang', 'Belum Diklaim', 2
WHERE NOT EXISTS (
	SELECT 1 FROM barang
	WHERE nama_barang = 'Laptop ASUS VivoBook' AND lokasi = 'Perpustakaan Lt. 2'
);

INSERT INTO barang (nama_barang, kategori, deskripsi, lokasi, status, status_claim, user_id)
SELECT 'KTM Mahasiswa', 'Dokumen', 'Atas nama Budi Santoso', 'Area Parkir Motor', 'Ditemukan', 'Belum Diklaim', 1
WHERE NOT EXISTS (
	SELECT 1 FROM barang
	WHERE nama_barang = 'KTM Mahasiswa' AND lokasi = 'Area Parkir Motor'
);

INSERT INTO barang (nama_barang, kategori, deskripsi, lokasi, status, status_claim, user_id)
SELECT 'Kunci Motor Honda', 'Aksesoris', 'Dengan gantungan warna merah', 'Kantin Fakultas', 'Ditemukan', 'Sudah Diklaim', 3
WHERE NOT EXISTS (
	SELECT 1 FROM barang
	WHERE nama_barang = 'Kunci Motor Honda' AND lokasi = 'Kantin Fakultas'
);

INSERT INTO barang (nama_barang, kategori, deskripsi, lokasi, status, status_claim, user_id)
SELECT 'Jaket Hitam', 'Pakaian', 'Ukuran L, hoodie', 'Lab Komputer', 'Hilang', 'Belum Diklaim', 2
WHERE NOT EXISTS (
	SELECT 1 FROM barang
	WHERE nama_barang = 'Jaket Hitam' AND lokasi = 'Lab Komputer'
);

INSERT INTO barang (nama_barang, kategori, deskripsi, lokasi, status, status_claim, user_id)
SELECT 'Flashdisk 32GB', 'Peralatan Kuliah', 'Merek Sandisk', 'Ruang A1.03', 'Ditemukan', 'Sudah Ditemukan', 1
WHERE NOT EXISTS (
	SELECT 1 FROM barang
	WHERE nama_barang = 'Flashdisk 32GB' AND lokasi = 'Ruang A1.03'
);

