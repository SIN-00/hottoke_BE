package hotketok.hotketok_server.Controller;

import hotketok.hotketok_server.Service.HouseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HouseController {

    private final HouseService houseService;

    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping("/search-address")
    public String searchAddress(@RequestParam String keyword) {
        return houseService.getRawJsonResponse(keyword);
    }
}
