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

    List<Auction> findAllByOrderByTitle();

    List<Auction> findAllByOrderByPrice();

    List<Auction> findAllByOrderByColor();

    List<Auction> findAllByOrderByEndDate();

    List<Auction> findByTitleContainsIgnoreCaseAndCarMakeContainsIgnoreCaseAndCarModelContainsIgnoreCaseAndColorContainsIgnoreCase
            (String title, String carMake, String carModel, String color);
}
