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
  const tickets = data.tickets;

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
      <div class="ticket">
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
          <button class="edit">Edit</button>
          <button class="delete">Delete</button>
        </div>
        <div class="ticket-buttons">
          <button class="subscribe">Subscribe</button>
          <button class="claim">Claim</button>
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
  const jsonData = '{"tickets": {"100": {"name": "Super-Ticket","assignee": 0,"status": 0,"subscribers": ["0", "10", "2", "3"],"description": "Hello World!","date_assigned": "2023-02-15","priority": 0},"618": {"name": "Super Lame Ticket","assignee": 10,"status": 2,"subscribers": [],"description": "This ticket sucks","date_assigned": "2023-02-04","priority": 2}},"inbox": {"0": ["Hello", "World!", "Boo"],"1": ["Leave me Here", "F-Society"]}}';
  displayJson(jsonData);
});