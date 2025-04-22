# Meeting Calendar Backend

A Spring Boot backend for managing meetings with REST API.

## Features
- CRUD operations for meetings
- Time conflict validation
- Data validation
- Error handling

## Setup
1. Clone the repository
2. Import into IntelliJ IDEA as Maven project
3. Run `MeetingCalendarApplication`

## API Endpoints
- `GET /api/meetings`: Get all meetings
- `POST /api/meetings`: Create new meeting
- `PUT /api/meetings/{id}`: Update meeting
- `DELETE /api/meetings/{id}`: Delete meeting

## Development History
- [Initial commit] Basic Spring Boot setup
- [Add Meeting entity] Created domain model
- [Implement DTOs] Added request/response DTOs
- [Add service layer] Implemented business logic
- [Add exception handling] Global exception handler
