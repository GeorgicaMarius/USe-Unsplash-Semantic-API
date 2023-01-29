import {Component, EventEmitter, Inject, OnInit, Output} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {AugmentParameters} from "../../../../types/augment-parameters";

@Component({
  selector: 'app-image-adjustment',
  templateUrl: './image-adjustment-component.html',
  styleUrls: ['./image-adjustment-component.scss'],

})

export class ImageAdjustmentComponent  {

  brightness = 0;
  contrast = 0;
  saturation = 0;
  shadow = 0;
  highlight = 0;
  sharpness = 0;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: {augmentParameters: AugmentParameters},
    public matDialogRef: MatDialogRef<ImageAdjustmentComponent>) {
      this.brightness = data.augmentParameters.brightness;
      this.contrast = data.augmentParameters.contrast;
      this.saturation = data.augmentParameters.saturation;
      this.shadow = data.augmentParameters.shadow;
      this.sharpness = data.augmentParameters.sharpness;
  }

  augmentPicture() {
    let augmentParameters : AugmentParameters = {
      brightness: this.brightness,
      contrast: this.contrast,
      saturation: this.saturation,
      shadow: this.shadow,
      highlight: this.highlight,
      sharpness: this.sharpness
    }
    this.matDialogRef.close(augmentParameters)
  }

}
