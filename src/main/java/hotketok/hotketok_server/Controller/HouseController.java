package hotketok.hotketok_server.Controller;

import hotketok.hotketok_server.Domain.House;
import hotketok.hotketok_server.Service.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class HouseController {

    private final HouseService houseService;

    @PostMapping("/address")
    public ResponseEntity<House> saveAddress(@RequestBody AddressRequest addressRequest) {
        House house = houseService.saveAddress(addressRequest.getAddress());
        return ResponseEntity.ok(house);
    }

    public static class AddressRequest {
        private String address;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}

