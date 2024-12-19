-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Anamakine: 127.0.0.1
-- Üretim Zamanı: 19 Ara 2024, 15:01:08
-- Sunucu sürümü: 10.4.32-MariaDB
-- PHP Sürümü: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Veritabanı: `notsistemi`
--

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `notes`
--

CREATE TABLE `notes` (
  `note_id` int(11) NOT NULL,
  `title` varchar(50) NOT NULL,
  `content` varchar(500) NOT NULL,
  `owner_id` int(11) NOT NULL,
  `imagePath` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `notes`
--

INSERT INTO `notes` (`note_id`, `title`, `content`, `owner_id`, `imagePath`) VALUES
(3, 'Baslik metin', 'Bu bir metin notudur.', 1, NULL),
(4, 'Baslik image', 'Bu bir resim notudur.', 1, '/path/to/default/image.jpg'),
(5, 'Baslik metin', 'Bu bir metin notudur.', 1, NULL),
(6, 'Baslik image', 'Bu bir resim notudur.', 1, '/path/to/default/image.jpg'),
(7, 'dff', '', 4, NULL),
(9, 'asasd', 'EELDQEyEPFDvdr4t9sV8V+gkyM/Uaej5V4osLL3D7FL9x8A=', 4, 'sdsd'),
(10, 'vbv', 'vbv', 4, NULL),
(12, 'bbv', '', 4, NULL),
(13, 'dsfasd', '', 4, NULL),
(14, 'dfssdf', 'dsffd', 4, NULL),
(15, 'cfgfggf', '', 4, NULL),
(16, 'bvbx', 'vbcvxz<<xz', 4, NULL),
(17, 'dsvzdva', '', 4, NULL),
(18, 'g', 'dvsda', 4, NULL),
(19, 'ed', 'EELDtWEmbHmLu5FfTmeymNJNQBdC7Whp+qvFx2qN77Rv6ss=', 12, NULL),
(20, 'dafsdad', 'EELDXclorJOMl2hcyl9fDjGwumr1TE/f3CODhe79Ze9QOUg=', 12, NULL),
(24, 'eda', 'EELDPzHIMriWUsEdckgE2ICZeVsqVwJsEZPWq9E1tMGNwBs=', 14, NULL),
(26, 'dsfsaa', 'EELDMs78yiQLmgw1ErhSHQktT7lUN4lLrhNiRzoY4mPlIbU=', 15, NULL),
(27, 'ddfd', 'EELDDPkWrOw+c1W//T6WLelqwFwlAREkqxeU4voKOu+qb4E=', 15, NULL);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `password`) VALUES
(1, 'gokalp', 'emailGokalp', '8080'),
(3, 'nh', 'hh', 'hh'),
(4, 'equrial', 'equ_67@hotmail.com', '123456'),
(5, 'bb', 'bb', 'bb'),
(6, 'ed', 'e', '123456'),
(7, 'd', 'dfd', 'fgdgfds'),
(8, 'vbv', 'vcbcv', 'vbvc'),
(9, 'equ', 'ev', '123456'),
(10, 'vcxz', 'vcxvz', 'vcxz'),
(11, 'gf', 'gfg', 'fg'),
(12, 'edanur', 'equrial@gmail.com', '123456'),
(13, 'edanurr', 'equrial@yandex.com', '123456'),
(14, 'edd', 'equ_37@hotmail.com', '123456'),
(15, 'edaa', 'eda@hotmail.com', '123456');

--
-- Dökümü yapılmış tablolar için indeksler
--

--
-- Tablo için indeksler `notes`
--
ALTER TABLE `notes`
  ADD PRIMARY KEY (`note_id`),
  ADD KEY `owner_id` (`owner_id`);

--
-- Tablo için indeksler `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Dökümü yapılmış tablolar için AUTO_INCREMENT değeri
--

--
-- Tablo için AUTO_INCREMENT değeri `notes`
--
ALTER TABLE `notes`
  MODIFY `note_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- Tablo için AUTO_INCREMENT değeri `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Dökümü yapılmış tablolar için kısıtlamalar
--

--
-- Tablo kısıtlamaları `notes`
--
ALTER TABLE `notes`
  ADD CONSTRAINT `notes_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
