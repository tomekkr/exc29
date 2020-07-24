package pl.javastart.sellegro.auction;

import org.springframework.stereotype.Service;
import pl.javastart.sellegro.repository.AuctionRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

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
        return auctionRepository.findByTitleContainsIgnoreCaseAndCarMakeContainsIgnoreCaseAndCarModelContainsIgnoreCaseAndColorContainsIgnoreCase
                (auctionFilters.getTitle(), auctionFilters.getCarMaker(), auctionFilters.getCarModel(), auctionFilters.getColor());
    }

    public List<Auction> findAllSorted(String sort) {
        List<Auction> auctions;
        switch (sort) {
            case "title":
                auctions = auctionRepository.findAllByOrderByTitle();
                break;
            case "price":
                auctions = auctionRepository.findAllByOrderByPrice();
                break;
            case "color":
                auctions = auctionRepository.findAllByOrderByColor();
                break;
            case "endDate":
                auctions = auctionRepository.findAllByOrderByEndDate();
                break;
            default:
                auctions = auctionRepository.findAll();
        }
        return auctions;
    }
}
