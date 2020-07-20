package pl.javastart.sellegro.auction;

import org.springframework.stereotype.Service;
import pl.javastart.sellegro.repository.AuctionRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuctionService {

    private AuctionRepository auctionRepository;


    private static final String[] ADJECTIVES = {"Niesamowity", "Jedyny taki", "IGŁA", "HIT", "Jak nowy",
            "Perełka", "OKAZJA", "Wyjątkowy"};

    public AuctionService(AuctionRepository auctionRepository) throws IOException {
        this.auctionRepository = auctionRepository;
    }

    @Transactional
    public void updateTitles() {
        Random random = new Random();
        List<Auction> auctionList = auctionRepository.findAll();
        for (Auction auction : auctionList) {
            String randomAdjective = ADJECTIVES[random.nextInt(ADJECTIVES.length)];
            auction.setTitle(randomAdjective + " " + auction.getCarMake() + " " + auction.getCarModel());
        }
    }

    public List<Auction> findAllForFilters(AuctionFilters auctionFilters) {
         List<Auction> auctions = auctionRepository.findAll();
        return auctions.stream()
                .filter(auction -> auctionFilters.getTitle() == null || auction.getTitle().toUpperCase().contains(auctionFilters.getTitle().toUpperCase()))
                .filter(auction -> auctionFilters.getCarMaker() == null || auction.getCarMake().toUpperCase().contains(auctionFilters.getCarMaker().toUpperCase()))
                .filter(auction -> auctionFilters.getCarModel() == null || auction.getCarModel().toUpperCase().contains(auctionFilters.getCarModel().toUpperCase()))
                .filter(auction -> auctionFilters.getColor() == null || auction.getColor().toUpperCase().contains(auctionFilters.getColor().toUpperCase()))
                .collect(Collectors.toList());
    }

    public List<Auction> findAllSorted(String sort) {
        List<Auction> auctions = auctionRepository.findAll();
        Comparator<Auction> comparator = Comparator.comparing(Auction::getTitle);
        if (sort.equals("title")) {
            comparator = Comparator.comparing(Auction::getTitle);
        } else if (sort.equals("price")) {
            comparator = Comparator.comparing(Auction::getPrice);
        } else if (sort.equals("color")) {
            comparator = Comparator.comparing(Auction::getColor);
        }
        else if (sort.equals("color")) {
            comparator = Comparator.comparing(Auction::getColor);
        } else if (sort.equals("endDate")) {
            comparator = Comparator.comparing(Auction::getEndDate);
        }
        return auctions.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
