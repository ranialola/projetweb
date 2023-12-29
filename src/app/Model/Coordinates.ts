export class Illustrations {
  id?: number;
  name!: string;
  description!: string;
  x!: number;
  y!: number;
  image?: File;
  components?: CustomComponent[]; 
  }
  
  export class CustomComponent {
  id?: number;
  componentName!: string;
  componentX!: number;
  componentY!: number;
  }