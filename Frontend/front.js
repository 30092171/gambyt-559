const baseURL = 'http://127.0.0.1:8080/api/v1'; // This needs to be updated and set if changed
const userID = 123;

var json = {
  "tickets": {
    "100": {
      "name": "Super-Ticket",
      "assignee": 0,
      "status": 0,
      "subscribers": ["0", "10", "2", "3"],
      "description": "Hello World!",
      "date_assigned": "2023-02-15",
      "priority": 0
    },
    "618": {
      "name": "Super Lame Ticket",
      "assignee": 10,
      "status": 2,
      "subscribers": [],
      "description": "This ticket sucks",
      "date_assigned": "2023-02-04",
      "priority": 2
    }
  },
  "inbox": {
    "0": ["Hello", "World!", "Boo"],
    "1": ["Leave me Here", "F-Society"]
  }
}

function displayJson(jsonData) {
  // Get a reference to the element where you want to display the tickets
  const ticketsContainer = document.getElementById('tickets-container');

  // Parse the JSON data and get the tickets object
  const data = JSON.parse(jsonData);
  const tickets = data;


  // Loop through the tickets object and generate HTML code for each ticket
  for (const ticketId in tickets) {
    const ticket = tickets[ticketId];
    var priority_value;
    var status_value;
    if (ticket.priority == 0) {
      priority_value = "Low";
    } else if (ticket.priority == 1) {
      priority_value = "Medium";
    } else if (tickets.priority == 2) {
      priority_value = "High";
    }
    if (ticket.status == 0) {
      status_value = "To-Do";
    } else if (ticket.status == 1) {
      status_value = "In-Progress";
    } else if (ticket.status == 2) {
      status_value = "Done";
    }

    const html = `
      <div class="ticket" id="${ticketId}">
        <div class="ticket-title">${ticket.name}</div>
        <div class="ticket-description">${ticket.description}</div>
        <div class="ticket-info">
          <div class="date"><strong>Date:</strong> ${ticket.date_assigned}</div>
          <div class="priority"><strong>Priority:</strong> ${priority_value}</div>
        </div>
        <div class="ticket-info">
          <div class="assignee"><strong>Assignee:</strong> ${ticket.assignee}</div>
          <div class="status"><strong>Status:</strong> ${status_value}</div>
        </div>
        <div class="ticket-buttons">
          <button class="edit" id="edit">Edit</button>
          <button class="delete deleteTicket" id="deleteTicket">Delete</button>
        </div>
        <div class="ticket-buttons">
          <button class="subscribe" id="subscribe">Subscribe</button>
          <button class="claim" id="claim">Claim</button>
        </div>
      </div>
    `;
    
    // Append the generated HTML code to the tickets container
    ticketsContainer.innerHTML += html;
  }
}

function clearTickets() {
  const ticketsContainer = document.getElementById('tickets-container');
  ticketsContainer.innerHTML = '';
}



// Get the HTML elements to show/hide
const createTicketSection = document.getElementById("create-ticket");
const viewTicketsSection = document.getElementById("view-tickets");
const inboxSection = document.getElementById("inbox");



// Get the menu items
const createTicketLink = document.querySelector("a[href='#create-ticket']");
const viewTicketsLink = document.querySelector("a[href='#view-tickets']");
const inboxLink = document.querySelector("a[href='#inbox']");

// Hide all sections except for the Create a Ticket section initially
viewTicketsSection.style.display = "none";
inboxSection.style.display = "none";

// Attach click event listeners to switch scenes
createTicketLink.addEventListener("click", function(event) {
  event.preventDefault();
  createTicketSection.style.display = "block";
  viewTicketsSection.style.display = "none";
  inboxSection.style.display = "none";
});

viewTicketsLink.addEventListener("click", function(event) {
  event.preventDefault();
  createTicketSection.style.display = "none";
  viewTicketsSection.style.display = "block";
  inboxSection.style.display = "none";
  console.log("Hello");
});

inboxLink.addEventListener("click", function(event) {
  event.preventDefault();
  createTicketSection.style.display = "none";
  viewTicketsSection.style.display = "none";
  inboxSection.style.display = "block";
});


document.addEventListener('DOMContentLoaded', function() {
  console.log("Page should have loaded");
  const jsonData = '{"0":{"name":"Joshies First Task","assignee":7,"status":1,"subscribers":[],"description":"Make a thing","dateAssigned":"2023-02-24","priority":1},"2":{"name":"Dynamic Ticket","assignee":20,"status":1,"subscribers":["1","2","3","20"],"description":"This ticket was updated!","dateAssigned":"2023-02-04","priority":0},"618":{"name":"Super Lame Ticket","assignee":10,"status":2,"subscribers":[],"description":"This ticket sucks","dateAssigned":"2023-02-04","priority":2},"6b02d43a720849cf8988a124e37c4189":{"name":"test2","assignee":1234,"status":0,"subscribers":["1","2","3"],"description":"this ticket sucks more","dateAssigned":"1997-01-01","priority":1},"ee3f6013b6674071ac616aaca0780ccb":{"name":"test3","assignee":1234,"status":0,"subscribers":[],"description":"this ticket sucks more","dateAssigned":"1997-01-01","priority":1},"80623425fc01491093ee7400c1c9e8e2":{"name":"Gregs Test","assignee":1234,"status":0,"subscribers":[],"description":"desc","dateAssigned":"2023-02-27","priority":2},"aa5b81d6dbc7436083740820eb9a75d0":{"name":"Gregs Test","assignee":1234,"status":0,"subscribers":[],"description":"desc","dateAssigned":"2023-02-27","priority":2},"1b673f7abd4c41c983fadc8d1c4d2ff0":{"name":"test","assignee":1234,"status":0,"subscribers":["1","2","3"],"description":"this ticket sucks more","dateAssigned":"1997-01-01","priority":1},"c45826299bc14b78b0a1bde49a1d7d79":{"name":"test","assignee":1234,"status":0,"subscribers":["1","2","3"],"description":"this ticket sucks more","dateAssigned":"1997-01-01","priority":1},"aafb471ef2b54fe4823778c3bcbac907":{"name":"This is my Second Test","assignee":431,"status":2,"subscribers":[],"description":"Pls work Pls","dateAssigned":"2023-02-22","priority":0}}';
//  const jsonData = '{"tickets": {"100": {"name": "Super-Ticket","assignee": 0,"status": 0,"subscribers": ["0", "10", "2", "3"],"description": "Hello World!","date_assigned": "2023-02-15","priority": 0},"618": {"name": "Super Lame Ticket","assignee": 10,"status": 2,"subscribers": [],"description": "This ticket sucks","date_assigned": "2023-02-04","priority": 2}},"inbox": {"0": ["Hello", "World!", "Boo"],"1": ["Leave me Here", "F-Society"]}}';
  displayJson(jsonData);

  // Get the elements for REST API calls
  const myInbox = document.getElementById("my-inbox");
  const allTickets = document.getElementById("all-tickets");
  const createNewTicket = document.getElementById("submitTicket");

  const subscribe = document.getElementsByClassName("subscribe");
  const unsubscribe = document.getElementsByClassName("unsubscribe");
  const del = document.getElementsByClassName("deleteTicket");
  const claim = document.getElementsByClassName("claim");

  // Add listeners
  // myInbox.addEventListener('click', getInbox);
  allTickets.addEventListener('click', getTickets);
  createNewTicket.addEventListener('click', postTicket);

  for (var i = 0; i < subscribe.length; i++) {
    subscribe[i].addEventListener('click', subTicket);
  }
  for (var i = 0; i < unsubscribe.length; i++) {
    unsubscribe[i].addEventListener('click', unsubTicket);
  }
  for (var i = 0; i < del.length; i++) {
    del[i].addEventListener('click', deleteTicket);
  }
  for (var i = 0; i < claim.length; i++) {
    claim[i].addEventListener('click', deleteTicket);
  }
});


// Functions

// function getInbox(event) {
//   const path = '/inbox/${userID}';
//   const url = baseURL + path;

//   fetch(url)
//     .then(response => response.json())
//     .then(data => console.log(data)) // DO stuff with Response
//     .catch(error => console.error(error));
// }

function getTickets(event) {
  const path = '/tasks';
  const url = baseURL + path;


  fetch(url)
    .then(response => response.json())
    .then(data => 
      {
        console.log(JSON.stringify(data));
        clearTickets();
        displayJson(JSON.stringify(data));
      }) // Do stuff with response
    .catch(error => console.error(error));
}

function postTicket(event) {
  console.log("Post ticket called");
  event.preventDefault();
  const form = event.target.parentNode;
  console.log(form)
  const formData = new FormData(form);
  const path = '/tasks';
  const url = baseURL + path;
  const options = {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(Object.fromEntries(formData))
  };
  console.log(options);

  fetch(url, options)
    .then(response => response.json())
    .then(data => console.log(data)) // Do Stuff with response
    .catch(error => console.error(error));
}

// function unsubTicket() {
//   //  Need to get ticket ID from inbox for ticket
//   const path = '/tasks/${ticketID}';
//   const url = baseURL + path;
//   const options = {
//     method: 'PUT',
//     headers: {'Content-Type': 'application/json'},
//     body: JSON.stringify(Object.fromEntries(formData))
//   };

//   fetch(url, options)
//     .then(response => response.json())
//     .then(data => console.log(data)) // Do Stuff with response
//     .catch(error => console.error(error));
// }

function subTicket(event) {
  var ticketID = event.srcElement.parentNode.parentNode.id;
  const path = '/tasks/${ticketID}';
  const url = baseURL + path;
  const options = {
    method: 'PUT',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(Object.fromEntries(formData))
  };

  fetch(url, options)
    .then(response => response.json())
    .then(data => console.log(data)) // Do Stuff with response
    .catch(error => console.error(error));
}

// THis one may need to be changed, No form really for this one. Will just need user id and ticket id
function claimTicket(event) {
  var ticketID = event.srcElement.parentNode.parentNode.id;
  const claimData = {
    assignee: '${userID}'
  };
  const path = '/tasks/${ticketID}';
  const url = baseURL + path;
  const options = {
    method: 'PUT',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(claimData)
  };

  fetch(url, options)
    .then(response => response.json())
    .then(data => console.log(data)) // Do Stuff with response
    .catch(error => console.error(error));
}

function deleteTicket(event) {
  var ticketID = event.srcElement.parentNode.parentNode.id;

  const path = '/tasks/${ticketID}';
  const url = baseURL + path;
  const options = {
    method: 'DELETE'
  };

  fetch(url, options)
    .then(response => {
      response.json();
    })
    .then(data => console.log(data)) // Do Stuff with response
    .catch(error => console.error(error));
}
