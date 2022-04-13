package tourGuide.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import tourGuide.user.Position;

@Data
public class UserAttractionResultDto {
	List<Position> attractions = new ArrayList<>();
	UserLatLongDto userLocation = new UserLatLongDto();
	

}
