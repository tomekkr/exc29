package pl.javastart.sellegro.auction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.javastart.sellegro.repository.AuctionRepository;

import java.util.List;

@Controller
public class AuctionController {

    private AuctionRepository auctionRepository;
    private AuctionService auctionService;

    @Autowired
    public AuctionController(AuctionRepository auctionRepository, AuctionService auctionService) {
        this.auctionRepository = auctionRepository;
        this.auctionService = auctionService;
        auctionService.updateTitles();
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("cars", auctionRepository.find4MostExpensive());
        return "home";
    }

    @GetMapping("/auctions")
    public String auctions(Model model,
                           @RequestParam(required = false) String sort,
                           AuctionFilters auctionFilters) {
        List<Auction> auctions;
        if(sort != null) {
            auctions = auctionService.findAllSorted(sort);
        } else {
            auctions = auctionService.findAllForFilters(auctionFilters);
        }

        model.addAttribute("cars", auctions);
        model.addAttribute("filters", auctionFilters);
        return "auctions";
    }
}
