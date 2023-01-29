import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CityInformation } from '../types/city-information.type';
import { CountryInformation } from '../types/country-information.type';

@Injectable({
  providedIn: 'root',
})
export class PhotoAdditionalInfoService {
  constructor(private httpClient: HttpClient) {}

  getCityInformation(cityName: string): Observable<CityInformation> {
    return this.httpClient.get<CityInformation>(
      `${environment.apiUrl}/additional-data/city/${cityName}`
    );
  }

  getCountryInformation(countryName: string): Observable<CountryInformation> {
    return this.httpClient.get<CountryInformation>(
      `${environment.apiUrl}/additional-data/country/${countryName}`
    );
  }
}
