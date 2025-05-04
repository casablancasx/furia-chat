################################################################################
# Stage 0: Build dependencies with Poetry
################################################################################
FROM python:3.12-slim AS builder

# Set environment variables
ENV PYTHONDONTWRITEBYTECODE=1 \
    PYTHONUNBUFFERED=1 \
    POETRY_VERSION=1.8.3 \
    POETRY_NO_INTERACTION=1 \
    POETRY_VIRTUALENVS_IN_PROJECT=true \
    POETRY_VIRTUALENVS_CREATE=true \
    POETRY_CACHE_DIR=/tmp/poetry_cache \
    PATH="/root/.local/bin:/opt/poetry/bin:$PATH"

WORKDIR /app

# Install Poetry
RUN pip install --no-cache-dir poetry==${POETRY_VERSION}

# Copy dependency definitions
COPY pyproject.toml poetry.lock ./

# Regenerate lock file metadata if needed
RUN poetry lock --no-update || true

# Install only production dependencies into in-project venv
RUN poetry install --only main --no-root --no-interaction --no-ansi \
 && rm -rf "$POETRY_CACHE_DIR" /root/.cache/pypoetry/* /root/.cache/pip/*

################################################################################
# Stage 1: Final runtime image
################################################################################
FROM python:3.12-slim AS final

# Set working directory
WORKDIR /app

# Copy virtual environment from builder
COPY --from=builder /app/.venv .venv

# Copy application code
COPY intent_model/ ./intent_model/
COPY app/ ./app/
COPY main.py ./

# Ensure venv scripts are on PATH
ENV PATH="/app/.venv/bin:$PATH"

# Expose application port
EXPOSE 8001

# Start the app
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8001"]
