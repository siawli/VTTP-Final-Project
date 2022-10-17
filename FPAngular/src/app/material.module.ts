import { NgModule } from "@angular/core";
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button'
import { MatIconModule } from '@angular/material/icon'
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatDialogModule } from '@angular/material/dialog';
import { MatTabsModule } from '@angular/material/tabs'

const matModules: any[] = [
    MatMenuModule, MatButtonModule, MatIconModule,
    MatFormFieldModule, MatInputModule, MatCardModule,
    MatDialogModule, MatTabsModule
]
  
  @NgModule({
    imports: matModules,
    exports: matModules
  })
  export class MaterialModule {}