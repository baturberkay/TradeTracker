SELECT 'CREATE DATABASE trade_tracker'
    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'trade_tracker')\gexec