import { Component, Input } from '@angular/core';
import { CityInformation } from 'src/app/features/photos/types/city-information.type';
import { CountryInformation } from 'src/app/features/photos/types/country-information.type';

@Component({
  selector: 'app-photo-additional-info',
  templateUrl: './photo-additional-info.component.html',
  styleUrls: ['./photo-additional-info.component.scss'],
})
export class PhotoAdditionalInfoComponent {
  @Input() city: CityInformation | undefined;
  @Input() country: CountryInformation | undefined;
}
