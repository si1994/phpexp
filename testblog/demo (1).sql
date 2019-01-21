-- phpMyAdmin SQL Dump
-- version 4.8.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 21, 2019 at 12:52 PM
-- Server version: 10.1.34-MariaDB
-- PHP Version: 7.1.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `demo`
--

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `id` int(10) UNSIGNED NOT NULL,
  `title` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `city`
--

CREATE TABLE `city` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `states_id` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `city`
--

INSERT INTO `city` (`id`, `name`, `states_id`, `created_at`, `updated_at`) VALUES
(1, 'varanshi', 1, NULL, NULL),
(2, 'kanpur', 1, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `countrie`
--

CREATE TABLE `countrie` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `countrie`
--

INSERT INTO `countrie` (`id`, `name`, `created_at`, `updated_at`) VALUES
(1, 'india', NULL, NULL),
(2, 'pakistan', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `migrations`
--

CREATE TABLE `migrations` (
  `id` int(10) UNSIGNED NOT NULL,
  `migration` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `batch` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `migrations`
--

INSERT INTO `migrations` (`id`, `migration`, `batch`) VALUES
(6, '2018_12_31_101540_create_reg_models_table', 5),
(7, '2018_12_31_111112_create_users_models_table', 6),
(10, '2014_10_12_000000_create_users_table', 7),
(11, '2014_10_12_100000_create_password_resets_table', 7),
(12, '2018_12_31_050816_create_items_table', 7),
(13, '2018_12_31_053455_create_posts_table', 7),
(14, '2018_12_31_063607_create_categories_table', 7),
(15, '2018_12_31_124632_create_user_datas_table', 7),
(16, '2019_01_09_053609_create_tests_table', 7),
(17, '2019_01_21_101433_create_country_state_city_tables', 8),
(18, '2019_01_21_101959_create_country_state_cities_table', 9),
(19, '2019_01_21_110110_create_states_table', 10),
(20, '2019_01_21_113252_create_prectices_table', 11);

-- --------------------------------------------------------

--
-- Table structure for table `password_resets`
--

CREATE TABLE `password_resets` (
  `email` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `token` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `posts`
--

CREATE TABLE `posts` (
  `id` int(10) UNSIGNED NOT NULL,
  `title` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `prectices`
--

CREATE TABLE `prectices` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `birthdate` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `states`
--

CREATE TABLE `states` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `countrie_id` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `states`
--

INSERT INTO `states` (`id`, `name`, `countrie_id`, `created_at`, `updated_at`) VALUES
(1, 'utter pradesh', 1, NULL, NULL),
(2, 'delhi', 1, NULL, NULL),
(3, 'balochistan', 2, NULL, NULL),
(4, 'shindh', 2, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `tests`
--

CREATE TABLE `tests` (
  `id` int(10) UNSIGNED NOT NULL,
  `title` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `type` int(2) NOT NULL DEFAULT '1' COMMENT '0=admin,1=user',
  `remember_token` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `password`, `type`, `remember_token`, `created_at`, `updated_at`, `deleted_at`) VALUES
(5, 'admin', 'admin@admin.com', '$2y$10$PgQK9fuItmKfrpz.1cOiS.XM5WtEoFKPfRpIFK9g.ty.Xl4n7Gwoq', 0, 'AVW6nPdcNcR5B20wTAQswD8lPGITLRkkK1KYRR2B2TepmtMs4xVvknfwmZpC', '2019-01-17 00:19:20', '2019-01-17 00:19:20', NULL),
(6, 'brinda', 'brinda@gmail.com', '$2y$10$n8BYyFxOGziT0uEGqd.4jeiIPJk5KsgCWnJqX90OJTtbmM.EjQk9e', 1, 'dJSZnZCzPU72hezr60gUDvPrtnntQaytphdtTpxSFCBOQNkEHijd8M4UpNG7', '2019-01-17 00:19:46', '2019-01-17 00:19:46', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `user_datas`
--

CREATE TABLE `user_datas` (
  `id` int(10) UNSIGNED NOT NULL,
  `fname` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lname` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `mobile` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `gender` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `address` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `profile_pic` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `user_datas`
--

INSERT INTO `user_datas` (`id`, `fname`, `lname`, `email`, `mobile`, `gender`, `address`, `password`, `profile_pic`, `created_at`, `updated_at`) VALUES
(2, 'jigar', 'bhuva', 'jigar@gmail.com', '7894561231', 'male', 'surat', '$2y$10$MhGllhnJaOao6lvdVVTkm.uu7wmO7b8E/WPXntZW633UPBMGgh2Fq', '[\"1547102765-car.jpg\",\"1547102765-download.jpeg\"]', '2019-01-10 01:16:05', '2019-01-10 01:16:05'),
(3, 'krishna', 'motivarsh', 'krishna@gmail.com', '9856235689', 'female', 'gandhinagar', '$2y$10$ifUnSo6k6BDblQne6tqes.uee7QddEn0Jzi11WYfC2VAKyZ527hlu', '[\"1547102814-butterfly.jpg\",\"1547102814-chan.jpg\"]', '2019-01-10 01:16:54', '2019-01-10 01:16:54'),
(4, 'hetal', 'bhalodiya', 'hetal@gmai.com', '7895621523', 'female', 'pune', '$2y$10$bdlZAaZtzS7ykhXq0PIztO.KbE8q9G2sDJWuMScr0dp5O5mE8iUHa', '[\"1547102913-login.jpg\",\"1547102913-mango.jpg\"]', '2019-01-10 01:18:33', '2019-01-10 01:18:33'),
(11, 'gracy', 'bhalodiya', 'gracy@gmail.com', '6778566745', 'female', 'pune', '$2y$10$jd1hsovnoV36Xt3LsPACzeKKZx8YTJPx83pBvHrEi18mXtQG/1UbO', '[\"1547876956-1.jpg\",\"1547876956-2.jpg\"]', '2019-01-19 00:19:16', '2019-01-19 00:19:16'),
(12, 'gracy', 'bhalodiya', 'gracy@gmail.com', '9856452536', 'female', 'pune', '$2y$10$MZm/wCHovpxJljUmCI0UWO5ZuKdX4FHTMIVwhfswD2C645SQiGxg2', '[\"1547904493-1.jpg\",\"1547904493-2.jpg\"]', '2019-01-19 07:58:13', '2019-01-19 07:58:13'),
(13, 'jigar', 'bhuva', 'jigar@gmail.com', '9033610203', 'male', 'surat', '$2y$10$YTFQeAQ3XP7Nb9mggi4g3ufb4GnlMuwv6tabnarR3/LcQrr7rXOpa', '[\"1547904532-2.jpg\",\"1547904532-3.jpg\"]', '2019-01-19 07:58:52', '2019-01-19 07:58:52'),
(14, 'anjana', 'valaviya', 'anjana@gmail.com', '9845625636', 'female', 'rajkot', '$2y$10$o8uLRb6TOA2wfX4RnPY.GOb950tPwRAfAtn1I4Q4.05J3nXw4lo.e', '[\"1547904575-1.jpg\",\"1547904575-3.jpg\"]', '2019-01-19 07:59:35', '2019-01-19 07:59:35'),
(15, 'aarti', 'bangoriya', 'aarti@gmail.com', '9874561238', 'female', 'pune', '$2y$10$81XdObmqoq/d6pdk7JIREeAmfbrSRhTvivZYm/NkAWrGLLKQxxdyO', '[\"1548062018-panda.jpg\",\"1548062018-sunflware.jpg\"]', '2019-01-19 08:00:23', '2019-01-21 03:43:38');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `city`
--
ALTER TABLE `city`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `countrie`
--
ALTER TABLE `countrie`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `migrations`
--
ALTER TABLE `migrations`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `password_resets`
--
ALTER TABLE `password_resets`
  ADD KEY `password_resets_email_index` (`email`);

--
-- Indexes for table `posts`
--
ALTER TABLE `posts`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `prectices`
--
ALTER TABLE `prectices`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `states`
--
ALTER TABLE `states`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tests`
--
ALTER TABLE `tests`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `users_email_unique` (`email`);

--
-- Indexes for table `user_datas`
--
ALTER TABLE `user_datas`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `city`
--
ALTER TABLE `city`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `countrie`
--
ALTER TABLE `countrie`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `migrations`
--
ALTER TABLE `migrations`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `posts`
--
ALTER TABLE `posts`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `prectices`
--
ALTER TABLE `prectices`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `states`
--
ALTER TABLE `states`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `tests`
--
ALTER TABLE `tests`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `user_datas`
--
ALTER TABLE `user_datas`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
