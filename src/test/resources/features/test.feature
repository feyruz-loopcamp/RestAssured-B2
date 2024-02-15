Feature: Verify that response body is matching

Given accept type is Json and adviser sends GET request to /api/v1/document/clients/all
Then status code is 200 and content type is application/json; charset=utf-8
And body index 0 matches data:
  | key       |  value  |
  |id         | 31      |
  |name       | 3tseT   |
  |clientType |  1      |
  | isActive  |  true   |
  | advisor   |  null   |
