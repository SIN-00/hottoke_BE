package hotketok.hotketok_server.Controller;

import hotketok.hotketok_server.DTO.AddressResponse;
import hotketok.hotketok_server.Domain.House;
import hotketok.hotketok_server.Service.AddressService;
import hotketok.hotketok_server.Service.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class HouseController {

    private final AddressService addressService;

    @GetMapping("/house/search")
    public ResponseEntity<AddressResponse> searchAddress(@RequestParam String keyword) {

        AddressResponse response = addressService.searchAddress(keyword);
        return ResponseEntity.ok(response);
    }

}

