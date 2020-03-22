const AWS = require("aws-sdk");
const crypto = require("crypto");

// Generate unique id with no external dependencies
const generateUUID = () => crypto.randomBytes(16).toString("hex");

// Initialising the DynamoDB SDK
const documentClient = new AWS.DynamoDB.DocumentClient();

//Todo: check droplet secret to remove duplicates

exports.handler = async event => {
  console.info("EVENT\n" + JSON.stringify(event, null, 2))
  const request = JSON.parse(JSON.stringify(event));
  const droplet = JSON.parse(request.body);
  console.info("Body\n" + JSON.stringify(droplet, null, 2))

  var res = {
    headers: {'Access-Control-Allow-Origin': '*'},
    statusCode: 0
  }

  //check input (droplet)
  var error = ""

  if (droplet.userId == undefined) {
    error = "userId missing "
  }

  if (droplet.postalCode == undefined) {
    error += "postalCode missing "
  } else {
    if (droplet.postalCode.length != 5) {
      error += " invalid lengh for postalCode"
    } else {
      for (var i = 0; i < droplet.postalCode.length; i++) {
        var c = droplet.postalCode.charAt(i);
        if (c >= '0' && c <= '9') {
            // it is a number
        } else {
            error += " invalid format in postalCode"
            break
        }
      }
    }
  }

  if (droplet.gender == undefined) {
    error += "gender missing "
  } else {
    if (!(droplet.gender >=-1 && droplet.gender <=3)) {
      error += "wrong value for gender "
    }
  }

  if (droplet.yearOfBirth == undefined) {
    error += "yearOfBirth missing "
  } else {
    if (!(droplet.yearOfBirth >=1900 && droplet.yearOfBirth <=2020)) {
      error += "wrong value for yearOfBirth "
    }
  }

  if (droplet.generalHealth == undefined) {
    error += "generalHealth missing "
  } else {
    if (!(droplet.generalHealth >=1 && droplet.generalHealth <=5)) {
      error += "wrong value for generalHealth "
    }
  }

  if (droplet.coronaVirus == undefined) {
    error += "coronaVirus missing "
  } else {
    if (!(droplet.coronaVirus >=-1 && droplet.coronaVirus <=5)) {
      error += "wrong value for coronaVirus "
    }
  }

  if (droplet.numberOfContacts == undefined) {
    error += "numberOfContacts missing "
  } else {
    if (!(droplet.numberOfContacts >=-1 && droplet.numberOfContacts <=1000)) {
      error += "wrong value for numberOfContacts "
    }
  }

  if (droplet.coughing == undefined) {
    error += "coughing missing "
  } else {
    if (!(droplet.coughing >=-1 && droplet.coughing <=3)) {
      error += "wrong value for coughing "
    }
  }

  if (droplet.temperature == undefined) {
    error += "temperature missing "
  } else {
    if (!(droplet.temperature >=-1 && droplet.temperature <=3)) {
      error += "wrong value for temperature "
    }
  }

  if (droplet.headache == undefined) {
    error += "headache missing "
  } else {
    if (!(droplet.headache >=-1 && droplet.headache <=3)) {
      error += "wrong value for headache "
    }
  }

  if (droplet.soreThroat == undefined) {
    error += "soreThroat missing "
  } else {
    if (!(droplet.soreThroat >=-1 && droplet.soreThroat <=3)) {
      error += "wrong value for soreThroat "
    }
  }

  if (droplet.runnyNose == undefined) {
    error += "runnyNose missing "
  } else {
    if (!(droplet.runnyNose >=-1 && droplet.runnyNose <=3)) {
      error += "wrong value for runnyNose "
    }
  }

  if (droplet.limbPain == undefined) {
    error += "limbPain missing "
  } else {
    if (!(droplet.limbPain >=-1 && droplet.limbPain <=3)) {
      error += "wrong value for limbPain "
    }
  }

  if (droplet.diarrhea == undefined) {
    error += "diarrhea missing "
  } else {
    if (!(droplet.diarrhea >=-1 && droplet.diarrhea <=2)) {
      error += "wrong value for diarrhea "
    }
  }

  if (droplet.loneliness == undefined) {
    error += "loneliness missing "
  } else {
    if (!(droplet.loneliness >=-1 && droplet.loneliness <=5)) {
      error += "wrong value for loneliness "
    }
  }

  if (droplet.insomnia == undefined) {
    error += "insomnia missing "
  } else {
    if (!(droplet.insomnia >=-1 && droplet.insomnia <=5)) {
      error += "wrong value for insomnia "
    }
  }

  if (error!="") {
    res.statusCode = 400;
    res.body = JSON.stringify({
        statusCode:400,
        body: error
      });

    return res;
  }


  const params = {
    TableName: "CovLake", // The name of your DynamoDB table
    Item: { // Creating an Item with a unique id and with the passed title
      id: generateUUID(),
      timestamp: Date.now() + "",
      userId: droplet.userId,
      postalCode: droplet.postalCode,
      gender: droplet.gender,
      yearOfBirth: droplet.yearOfBirth,
      generalHealth: droplet.generalHealth,
      coronaVirus: droplet.coronaVirus,
      numberOfContacts: droplet.numberOfContacts,
      coughing: droplet.coughing,
      temperature: droplet.temperature,
      headache: droplet.headache,
      soreThroat: droplet.soreThroat,
      runnyNose: droplet.runnyNose,
      limbPain: droplet.limbPain,
      diarrhea: droplet.diarrhea,
      loneliness: droplet.loneliness,
      insomnia: droplet.insomnia
    }
  };

  try {
    // Utilising the put method to insert an item into the table (https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/GettingStarted.NodeJs.03.html#GettingStarted.NodeJs.03.01)
    await documentClient.put(params).promise();
    res.statusCode = 200;
    res.body = JSON.stringify({
        statusCode:200
      });

    return res; // Returning a 200 if the item has been inserted
  } catch (e) {
    res.statusCode = 500;
    res.body = JSON.stringify({
        statusCode: 500,
        error: JSON.stringify(e)
      })
    return res;
  }
};
