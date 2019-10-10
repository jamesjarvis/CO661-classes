public class Request<Value,Response> {

  public Channel<Response>.Endpoint responseChan;
  public Value value;

  public Request(Value value, Channel<Response>.Endpoint responseChan) {
    this.value = value;
    this.responseChan = responseChan;
  }
}