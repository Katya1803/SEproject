export class OrderHistory {
  constructor(
    public id: string,
    public orderTrackingNumber: string,
    public totalQuantity: number,
    public dateCreated: Date,
    public totalPrice: number
  ) {}
}
