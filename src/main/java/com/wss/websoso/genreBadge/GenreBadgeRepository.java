package com.wss.websoso.genreBadge;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreBadgeRepository extends JpaRepository<GenreBadge, Long> {

    GenreBadge findByGenreBadgeName(String genreBadge);
}
