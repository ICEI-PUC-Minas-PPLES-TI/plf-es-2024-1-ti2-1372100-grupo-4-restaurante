import type { PageServerLoad } from "./$types";

let endpoint = "http://localhost:8080";

export const load = (async ({ params }) => {
  const id = params.id;
  const response = await fetch(`${endpoint}/mesas/${id}`).then((e) => e.json());
  const { nomeMesa } = response;
  return { id, nomeMesa };
}) satisfies PageServerLoad;
