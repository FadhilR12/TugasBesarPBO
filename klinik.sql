-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 30, 2026 at 06:26 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `klinik`
--

-- --------------------------------------------------------

--
-- Table structure for table `dokter`
--

CREATE TABLE `dokter` (
  `id` int(11) NOT NULL,
  `spesialis` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `dokter`
--

INSERT INTO `dokter` (`id`, `spesialis`) VALUES
(1, 'Penyakit Dalam'),
(2, 'Anak');

-- --------------------------------------------------------

--
-- Table structure for table `janjitemu`
--

CREATE TABLE `janjitemu` (
  `idJanji` int(11) NOT NULL,
  `tanggal` date NOT NULL,
  `status` varchar(50) NOT NULL DEFAULT 'menunggu',
  `idDokter` int(11) NOT NULL,
  `idPasien` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `janjitemu`
--

INSERT INTO `janjitemu` (`idJanji`, `tanggal`, `status`, `idDokter`, `idPasien`) VALUES
(1, '2024-06-01', 'selesai', 1, 3),
(2, '2024-06-02', 'selesai', 2, 4),
(3, '2024-06-03', 'selesai', 1, 5),
(4, '2024-06-04', 'menunggu', 2, 6),
(5, '2024-06-05', 'menunggu', 1, 7);

-- --------------------------------------------------------

--
-- Table structure for table `obat`
--

CREATE TABLE `obat` (
  `idObat` int(11) NOT NULL,
  `namaObat` varchar(100) NOT NULL,
  `harga` double NOT NULL,
  `idResep` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `obat`
--

INSERT INTO `obat` (`idObat`, `namaObat`, `harga`, `idResep`) VALUES
(1, 'Amlodipin 5mg', 15000, 1),
(2, 'Amoxicillin 500mg', 8000, 2),
(3, 'Metformin 500mg', 12000, 3),
(4, 'Oralit', 5000, 4),
(5, 'Antasida Doen', 6000, 5);

-- --------------------------------------------------------

--
-- Table structure for table `pasien`
--

CREATE TABLE `pasien` (
  `id` int(11) NOT NULL,
  `noRekamMedis` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pasien`
--

INSERT INTO `pasien` (`id`, `noRekamMedis`) VALUES
(3, 'RM-2024-001'),
(4, 'RM-2024-002'),
(5, 'RM-2024-003'),
(6, 'RM-2024-004'),
(7, 'RM-2024-005');

-- --------------------------------------------------------

--
-- Table structure for table `pembayaran`
--

CREATE TABLE `pembayaran` (
  `idBayar` int(11) NOT NULL,
  `jumlah` double NOT NULL,
  `status` varchar(50) NOT NULL DEFAULT 'belum_bayar',
  `idJanji` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pembayaran`
--

INSERT INTO `pembayaran` (`idBayar`, `jumlah`, `status`, `idJanji`) VALUES
(1, 150000, 'lunas', 1),
(2, 120000, 'lunas', 2),
(3, 200000, 'lunas', 3),
(4, 80000, 'belum_bayar', 4),
(5, 100000, 'belum_bayar', 5);

-- --------------------------------------------------------

--
-- Table structure for table `rekammedis`
--

CREATE TABLE `rekammedis` (
  `idRekam` int(11) NOT NULL,
  `diagnosa` text NOT NULL,
  `tindakan` text NOT NULL,
  `idPasien` int(11) NOT NULL,
  `idDokter` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rekammedis`
--

INSERT INTO `rekammedis` (`idRekam`, `diagnosa`, `tindakan`, `idPasien`, `idDokter`) VALUES
(1, 'Hipertensi Grade I', 'Pemberian obat antihipertensi', 3, 1),
(2, 'ISPA (Batuk & Pilek)', 'Pemberian antibiotik & antipiretik', 4, 2),
(3, 'Diabetes Mellitus Tipe 2', 'Edukasi diet & pemberian insulin', 5, 1),
(4, 'Diare Akut', 'Rehidrasi oral & pemberian zinc', 6, 2),
(5, 'Gastritis', 'Pemberian antasida & diet lunak', 7, 1);

-- --------------------------------------------------------

--
-- Table structure for table `resep`
--

CREATE TABLE `resep` (
  `idResep` int(11) NOT NULL,
  `tanggal` date NOT NULL,
  `idDokter` int(11) NOT NULL,
  `idPasien` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `resep`
--

INSERT INTO `resep` (`idResep`, `tanggal`, `idDokter`, `idPasien`) VALUES
(1, '2024-06-01', 1, 3),
(2, '2024-06-02', 2, 4),
(3, '2024-06-03', 1, 5),
(4, '2024-06-04', 2, 6),
(5, '2024-06-05', 1, 7);

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`id`) VALUES
(8),
(9),
(10);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `alamat` varchar(255) NOT NULL,
  `noHP` varchar(20) NOT NULL,
  `role` enum('dokter','pasien','staff') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `nama`, `alamat`, `noHP`, `role`) VALUES
(1, 'Dr. Andi Pratama', 'Jl. Merdeka No.1, Bandung', '081211110001', 'dokter'),
(2, 'Dr. Sari Dewi', 'Jl. Sudirman No.5, Bandung', '081211110002', 'dokter'),
(3, 'Budi Santoso', 'Jl. Pahlawan No.10, Cimahi', '081311110001', 'pasien'),
(4, 'Citra Lestari', 'Jl. Bunga Raya No.3, Bandung', '081311110002', 'pasien'),
(5, 'Deni Ramadhan', 'Jl. Gunung Batu No.7, Bandung', '081311110003', 'pasien'),
(6, 'Eka Putri', 'Jl. Pasteur No.12, Bandung', '081311110004', 'pasien'),
(7, 'Fajar Nugroho', 'Jl. Setiabudhi No.9, Bandung', '081311110005', 'pasien'),
(8, 'Gina Rahayu', 'Jl. Kopo No.21, Bandung', '081411110001', 'staff'),
(9, 'Hendra Kusuma', 'Jl. Cihampelas No.4, Bandung', '081411110002', 'staff'),
(10, 'Indah Permatasari', 'Jl. Dago No.8, Bandung', '081411110003', 'staff');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `dokter`
--
ALTER TABLE `dokter`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `janjitemu`
--
ALTER TABLE `janjitemu`
  ADD PRIMARY KEY (`idJanji`),
  ADD KEY `fk_janji_dokter` (`idDokter`),
  ADD KEY `fk_janji_pasien` (`idPasien`);

--
-- Indexes for table `obat`
--
ALTER TABLE `obat`
  ADD PRIMARY KEY (`idObat`),
  ADD KEY `fk_obat_resep` (`idResep`);

--
-- Indexes for table `pasien`
--
ALTER TABLE `pasien`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `noRekamMedis` (`noRekamMedis`);

--
-- Indexes for table `pembayaran`
--
ALTER TABLE `pembayaran`
  ADD PRIMARY KEY (`idBayar`),
  ADD UNIQUE KEY `idJanji` (`idJanji`);

--
-- Indexes for table `rekammedis`
--
ALTER TABLE `rekammedis`
  ADD PRIMARY KEY (`idRekam`),
  ADD KEY `fk_rekam_pasien` (`idPasien`),
  ADD KEY `fk_rekam_dokter` (`idDokter`);

--
-- Indexes for table `resep`
--
ALTER TABLE `resep`
  ADD PRIMARY KEY (`idResep`),
  ADD KEY `fk_resep_dokter` (`idDokter`),
  ADD KEY `fk_resep_pasien` (`idPasien`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `janjitemu`
--
ALTER TABLE `janjitemu`
  MODIFY `idJanji` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `obat`
--
ALTER TABLE `obat`
  MODIFY `idObat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `pembayaran`
--
ALTER TABLE `pembayaran`
  MODIFY `idBayar` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `rekammedis`
--
ALTER TABLE `rekammedis`
  MODIFY `idRekam` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `resep`
--
ALTER TABLE `resep`
  MODIFY `idResep` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `dokter`
--
ALTER TABLE `dokter`
  ADD CONSTRAINT `fk_dokter_user` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `janjitemu`
--
ALTER TABLE `janjitemu`
  ADD CONSTRAINT `fk_janji_dokter` FOREIGN KEY (`idDokter`) REFERENCES `dokter` (`id`),
  ADD CONSTRAINT `fk_janji_pasien` FOREIGN KEY (`idPasien`) REFERENCES `pasien` (`id`);

--
-- Constraints for table `obat`
--
ALTER TABLE `obat`
  ADD CONSTRAINT `fk_obat_resep` FOREIGN KEY (`idResep`) REFERENCES `resep` (`idResep`);

--
-- Constraints for table `pasien`
--
ALTER TABLE `pasien`
  ADD CONSTRAINT `fk_pasien_user` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `pembayaran`
--
ALTER TABLE `pembayaran`
  ADD CONSTRAINT `fk_bayar_janji` FOREIGN KEY (`idJanji`) REFERENCES `janjitemu` (`idJanji`);

--
-- Constraints for table `rekammedis`
--
ALTER TABLE `rekammedis`
  ADD CONSTRAINT `fk_rekam_dokter` FOREIGN KEY (`idDokter`) REFERENCES `dokter` (`id`),
  ADD CONSTRAINT `fk_rekam_pasien` FOREIGN KEY (`idPasien`) REFERENCES `pasien` (`id`);

--
-- Constraints for table `resep`
--
ALTER TABLE `resep`
  ADD CONSTRAINT `fk_resep_dokter` FOREIGN KEY (`idDokter`) REFERENCES `dokter` (`id`),
  ADD CONSTRAINT `fk_resep_pasien` FOREIGN KEY (`idPasien`) REFERENCES `pasien` (`id`);

--
-- Constraints for table `staff`
--
ALTER TABLE `staff`
  ADD CONSTRAINT `fk_staff_user` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
