# Booter McGooter
A plugin for Mirth Connect to set application properties stored in the DB from environment variables 

## Usage

Install the plugin. No signing cert is necessary since this is a server-side plugin with no client-side UI.

Set the following environment variables:
- `MC_SERVER_NAME`
- `MC_ENV_NAME`

When MC starts the plugin will register an event listener for the "server startup" event. When that event fires, the plugin knows Mirth is totally done starting and loading configs from the DB. The plugins event handler will then update `ServerSettings` with the environment name and server name

## Inspiration

As usual, I was hacking on something at work and asked the brain trust in Slack for ideas before I wasted a whole day running in circles.

The goal I had was to set environment name and server name at boot time from environment variables. These can be set in the MC UI. They are stored as `core` properties in the `configurations` table. It is possible to pre-load a DB or execute `psql` statements from the Docker startup script. That requires some fiddly SQL but @kpalang sketched out how to do it.

@tonygermano hit on the idea of leveraging the [new server startup event](https://github.com/nextgenhealthcare/connect/pull/4861) feature from 4.4.0.