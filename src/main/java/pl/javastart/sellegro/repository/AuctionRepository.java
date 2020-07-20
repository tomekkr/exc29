package pl.javastart.sellegro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.javastart.sellegro.auction.Auction;

import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
    List<Auction> findAll();

    @Query(value = "SELECT * from Auction ORDER BY price DESC LIMIT 4", nativeQuery = true)
    List<Auction> find4MostExpensive();
}
